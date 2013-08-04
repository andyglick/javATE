package it.amattioli.applicate.sessions;

import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandEventSupport;
import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.commands.CommandResult;

/**
 * Un topic per gli eventi generati dai command.
 * Gli oggetti di questa classe si pongono come intermediari tra i command
 * che generano gli eventi e i listener che sono in ascolto disaccoppiandoli
 * tra di loro. Un oggetto di questa classe si può mettere in ascolto su dei
 * command e fare un broadcast dell'evento inviato a tutti gli oggetti che
 * sono in ascolto sulla coda stessa.
 *
 * @author a.mattioli
 *
 */
public class CommandEventTopic implements CommandListener {
    private CommandEventSupport support = new CommandEventSupport();

    /**
     * Il topic si mette in ascolto su dei command e quando questi inviano
     * un evento verrà chiamato questo metodo. L'evento inviato sarà
     * re-inviato a tutti gli oggetti in ascolto su questo topic senza
     * ulteriori processamenti.
     *
     * @param event l'evento generato dal command
     */
    public void commandDone(final CommandEvent event) {
        support.fireCommandEvent(event);
    }

    /**
     * Aggiunge un {@link CommandListener} a quelli in ascolto degli eventi
     * inviati a questo topic.
     *
     * @param listener l'oggetto da registrare come listener
     */
    public void addCommandListener(final CommandListener listener) {
        support.addListener(listener);
    }

    /**
     * Aggiunge un {@link CommandListener} a quelli in ascolto degli eventi
     * inviati a questo topic. Al listener verranno inviati solamente gli
     * eventi il cui {@link CommandResult} è uno di quelli elencati.
     *
     * @param listener l'oggetto da registrare come listener
     * @param results i possibili {@link CommandResult} che il listener vuole
     *        ascoltare
     */
    public void addCommandListener(final CommandListener listener, final CommandResult... results) {
        support.addListener(listener, results);
    }

    /**
     * Rimuove un listener da quelli in ascolto.
     * 
     * @param listener il listener da rimuovere.
     */
    public void removeListener(CommandListener listener) {
        support.removeListener(listener);
    }
}
