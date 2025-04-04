public class Main {
    public static void main(String[] args) {
        Professor professor = new Professor("Vinicius");
        Disciplina poo = new Disciplina("POO");
        Turma turma = new Turma(professor, poo);
        Aluno andre = new Aluno("André");
        Aluno guilherme = new Aluno("Guilherme");
        Aluno murilo = new Aluno("Murilo");

        turma.matricular(andre);
        turma.matricular(guilherme);
        turma.matricular(murilo);

        turma.detalhar(); // lista disciplina, professor e aluno
        System.out.println(turma.getProfessor().getNome().equals(professor.getNome()));

        turma.setNota(andre, 10);

        turma.getNota(andre);
    }
}
