package com.proxy.apresentacao;

import java.io.IOException;

import com.proxy.models.User;
import com.proxy.negocio.UserAPI;
import com.proxy.negocio.UserAPIProxyCache;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("=== DEMONSTRAÇÃO DO PADRÃO PROXY COM CACHE ===\n");

    UserAPIProxyCache userAPIProxy = new UserAPIProxyCache(new UserAPI());

    System.out.println("--- Consultando 4 usuários ---\n");

    System.out.println("1) Buscando usuário 1:");
    User user1 = new User(userAPIProxy.info("1"));
    System.out.println(user1);
    System.out.println();

    System.out.println("2) Buscando usuário 2:");
    User user2 = new User(userAPIProxy.info("2"));
    System.out.println(user2);
    System.out.println();

    System.out.println("3) Buscando usuário 3:");
    User user3 = new User(userAPIProxy.info("3"));
    System.out.println(user3);
    System.out.println();

    System.out.println("4) Buscando usuário 1 novamente:");
    User user1Again = new User(userAPIProxy.info("1"));
    System.out.println(user1Again);
    userAPIProxy.cacheStatus();

    System.out.println("\n--- Adicionando mais um usuário ---\n");

    System.out.println("5) Buscando usuário 4:");
    User user4 = new User(userAPIProxy.info("4"));
    System.out.println(user4);

    userAPIProxy.cacheStatus();

    System.out.println("\n--- Testando se usuário 1 foi removido do cache ---\n");

    System.out.println("6) Buscando usuário 1 mais uma vez:");
    User user1Final = new User(userAPIProxy.info("1"));
    System.out.println(user1Final);
    System.out.println();

    System.out.println("7) Buscando usuário 3 novamente:");
    User user3Again = new User(userAPIProxy.info("3"));
    System.out.println(user3Again);

    userAPIProxy.cacheStatus();

    System.out.println("\n=== FIM DA DEMONSTRAÇÃO ===");
  }
}
