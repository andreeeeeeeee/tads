import AsyncStorage from "@react-native-async-storage/async-storage";
import {
  createContext,
  useContext,
  useEffect,
  useRef,
  useState,
  type ReactNode,
} from "react";

import type { Sessao, Usuario } from "@/models/usuario";
import { ApiError, api, definirTratamentoDe401 } from "@/services/api";

// CONTEXTO DE AUTENTICAÇÃO — src/contexts/auth-context.tsx
//
// O problema: quem está logado interessa a MUITAS telas (o cabeçalho mostra
// o nome, o layout decide quais rotas existem, a lista busca dados com o
// token). Passar isso por props, tela a tela, não escala — e o layout raiz
// nem tem como passar props para as telas do Expo Router.
//
// A solução da Context API tem três peças:
//
//   1. createContext()  — cria o "canal";
//   2. <AuthProvider>   — o componente que GUARDA o estado e o coloca no canal,
//                         envolvendo a parte da árvore que precisa dele;
//   3. useAuth()        — o hook que qualquer componente abaixo do provider
//                         usa para LER o canal.
//
// Quando o valor do provider muda (alguém entrou ou saiu), todos os
// componentes que chamaram useAuth() são re-renderizados — como se o estado
// fosse deles.

const CHAVE_SESSAO = "@aula06/sessao";
const AVISO_SESSAO_EXPIRADA = "Sua sessão expirou. Entre novamente.";

// O que fica disponível para quem chamar useAuth().
interface AuthContextValue {
  // null = ninguém logado. É este valor que os layouts consultam para
  // decidir quais rotas estão acessíveis.
  usuario: Usuario | null;
  // O token vai no cabeçalho de toda requisição autenticada.
  token: string | null;
  // Verdadeiro só enquanto um login ou cadastro está em andamento.
  carregando: boolean;
  // Verdadeiro até o app decidir se há sessão salva. Enquanto isso o
  // layout mostra a espera — o login não pode aparecer antes.
  restaurando: boolean;
  // Preenchido quando um 401 autenticado derruba a sessão. O logout
  // voluntário deixa isto null.
  avisoSessao: string | null;
  // Servidor fora do ar na abertura (status 0). O token salvo fica.
  erroDeConexao: string | null;
  entrar: (email: string, senha: string) => Promise<void>;
  cadastrar: (nome: string, email: string, senha: string) => Promise<void>;
  sair: () => Promise<void>;
  tentarRestaurar: () => Promise<void>;
}

// 1. O canal. O valor inicial (null) só é visto por quem chamar useAuth()
// FORA de um provider — e o hook trata isso como erro de programação.
const AuthContext = createContext<AuthContextValue | null>(null);

function lerTokenSalvo(bruto: string): string | null {
  try {
    const dados: unknown = JSON.parse(bruto);
    if (
      typeof dados === "object" &&
      dados !== null &&
      "token" in dados &&
      typeof dados.token === "string" &&
      dados.token.length > 0
    ) {
      return dados.token;
    }
  } catch {
    // JSON corrompido: quem chamou descarta a chave.
  }
  return null;
}

// 2. O provider: um componente comum, com useState comum. A única diferença
// para qualquer outro componente é que ele entrega o estado via contexto,
// e não via props.
export function AuthProvider({ children }: { children: ReactNode }) {
  // Token e usuário mudam sempre juntos, então vivem em um único estado.
  const [sessao, setSessao] = useState<Sessao | null>(null);
  const [carregando, setCarregando] = useState(false);
  const [restaurando, setRestaurando] = useState(true);
  const [avisoSessao, setAvisoSessao] = useState<string | null>(null);
  const [erroDeConexao, setErroDeConexao] = useState<string | null>(null);

  // O callback de 401 é registrado uma vez. Ele precisa da sessão atual
  // sem ficar preso no closure da primeira renderização.
  const sessaoRef = useRef<Sessao | null>(null);
  sessaoRef.current = sessao;
  const saindoDeProposito = useRef(false);

  async function gravarSessao(nova: Sessao) {
    await AsyncStorage.setItem(CHAVE_SESSAO, JSON.stringify(nova));
    sessaoRef.current = nova;
    setAvisoSessao(null);
    setErroDeConexao(null);
    setSessao(nova);
  }

  async function limparSessaoLocal(expirada: boolean) {
    await AsyncStorage.removeItem(CHAVE_SESSAO).catch(() => {});
    sessaoRef.current = null;
    setSessao(null);
    setAvisoSessao(expirada ? AVISO_SESSAO_EXPIRADA : null);
  }

  async function restaurar() {
    setRestaurando(true);
    setErroDeConexao(null);

    let tokenSalvo: string | null = null;
    try {
      const bruto = await AsyncStorage.getItem(CHAVE_SESSAO);
      if (bruto) {
        tokenSalvo = lerTokenSalvo(bruto);
        if (!tokenSalvo) await AsyncStorage.removeItem(CHAVE_SESSAO);
      }
    } catch {
      tokenSalvo = null;
    }

    if (!tokenSalvo) {
      setRestaurando(false);
      return;
    }

    try {
      // Não confiamos no usuário gravado no aparelho: o servidor confirma.
      const usuario = await api.perfil(tokenSalvo);
      sessaoRef.current = { token: tokenSalvo, usuario };
      setSessao({ token: tokenSalvo, usuario });
    } catch (e) {
      if (e instanceof ApiError && e.status === 401) {
        // O callback de 401 já limpou. Garantimos o aviso mesmo assim.
        await limparSessaoLocal(true);
      } else {
        // Status 0 (ou outro erro que não seja 401): o token pode continuar
        // válido. Não apagamos e não mostramos o login.
        const mensagem =
          e instanceof ApiError
            ? e.message
            : "Não foi possível confirmar a sessão. O servidor está rodando?";
        setErroDeConexao(mensagem);
      }
    } finally {
      setRestaurando(false);
    }
  }

  useEffect(() => {
    definirTratamentoDe401(() => {
      // Logout voluntário também pode receber 401 se o servidor já caiu.
      // Esse caso não é "sessão expirou" — a pessoa pediu para sair.
      if (saindoDeProposito.current) return;
      void limparSessaoLocal(true);
    });
    void restaurar();
    // O registro e a leitura inicial acontecem uma vez, na montagem.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  async function entrar(email: string, senha: string) {
    // Se o servidor recusar, api.login lança um erro — e ele sobe até a tela
    // de login, que é quem sabe mostrar a mensagem. O provider não trata.
    setCarregando(true);
    try {
      const novaSessao = await api.login(email, senha);
      await gravarSessao(novaSessao);
      // Repare: NENHUMA navegação aqui. Mudar o estado basta — o layout raiz
      // lê `usuario` e troca as rotas disponíveis sozinho.
    } finally {
      setCarregando(false);
    }
  }

  async function cadastrar(nome: string, email: string, senha: string) {
    // O servidor já devolve a sessão no cadastro: quem se cadastra entra.
    setCarregando(true);
    try {
      const novaSessao = await api.cadastrar(nome, email, senha);
      await gravarSessao(novaSessao);
    } finally {
      setCarregando(false);
    }
  }

  async function sair() {
    // Avisa o servidor para invalidar o token, mas o logout local acontece
    // de qualquer jeito — mesmo com o servidor fora do ar.
    saindoDeProposito.current = true;
    const tokenAtual = sessaoRef.current?.token;
    try {
      await limparSessaoLocal(false);
      if (tokenAtual) {
        await api.sair(tokenAtual).catch(() => {});
      }
    } finally {
      saindoDeProposito.current = false;
    }
  }

  // No React 19 o próprio contexto é o provider. Antes se escrevia
  // <AuthContext.Provider value={...}> — as duas formas funcionam.
  return (
    <AuthContext
      value={{
        usuario: sessao?.usuario ?? null,
        token: sessao?.token ?? null,
        carregando,
        restaurando,
        avisoSessao,
        erroDeConexao,
        entrar,
        cadastrar,
        sair,
        tentarRestaurar: restaurar,
      }}
    >
      {children}
    </AuthContext>
  );
}

// 3. O hook. Encapsula o useContext e falha cedo, com uma mensagem clara,
// se alguém esquecer o provider — em vez de um "cannot read property
// 'usuario' of null" em uma tela qualquer.
export function useAuth(): AuthContextValue {
  const contexto = useContext(AuthContext);
  if (contexto === null) {
    throw new Error("useAuth() só pode ser usado dentro de <AuthProvider>.");
  }
  return contexto;
}
