/**
 * 
 */
package org.tp.instruction.queue;

import java.util.PriorityQueue;

import org.tp.instruction.message.InstructionMessage;

/**
 * Priotity Queue with FIFO capability for same priority elements.
 * 
 * @author Janice Alvares
 * 
 */
public class InstructionQueue extends PriorityQueue<InstructionMessage> {

    private static final long serialVersionUID = 1021594942558949236L;

    public void enqueue(InstructionMessage message) {
	this.offer(message);
    }

    public InstructionMessage dequeue() {
	return this.remove();
    }

    @Override
    public InstructionMessage peek() {
	return super.peek();
    }

    public int count() {
	return this.size();

    }

    @Override
    public boolean isEmpty() {
	return super.isEmpty();

    }
}
