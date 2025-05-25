public class AgendaNaoDisponivelException extends Exception {
  public AgendaNaoDisponivelException() {
    super("Horário não disponível para agendamento.");
  }
}
