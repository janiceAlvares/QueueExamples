/**
 * 
 */
package org.tp.instruction.message;

import org.tp.instruction.queue.InstructionQueue;

/**
 * Receives incoming messages
 * 
 * @author Janice Alvares
 * 
 */
public interface MessageReceiver {
    /**
     * This method builds instances of {@link InstructionMessage} from the
     * incoming message string and enqueues messages to the
     * {@link InstructionQueue}.
     * 
     * @param message
     *            message
     * @throws Exception
     */
    void receive(String message) throws Exception;
}
