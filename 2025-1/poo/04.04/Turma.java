import java.util.ArrayList;
import java.util.List;

public class Turma {
    private Professor professor;
    private Disciplina disciplina;
    private List<Aluno> alunos;

    Turma(Professor professor, Disciplina disciplina) {
        this.professor = professor;
        this.disciplina = disciplina;
        this.alunos = new ArrayList<>();
    }

    Professor getProfessor() {
        return professor;
    }

    void matricular(Aluno aluno) {
        this.alunos.add(aluno);
        return;
    }

    void detalhar() {
        System.out.println(this.disciplina +"\n" +professor+"\n" + alunos);
    }

    void setNota(Aluno aluno, int nota){
        aluno.setNota(nota);
    }

    void getNota(Aluno aluno) {
        System.out.println(aluno.getNota());
    }
}
