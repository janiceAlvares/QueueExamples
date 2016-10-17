/**
 * 
 */
package org.tp.instruction.processor;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;
import org.tp.instruction.message.InstructionMessage;
import org.tp.instruction.queue.InstructionQueue;

/**
 * Test Class to test {@link InstructionProcessor}.
 * 
 * @author Janice Alvares
 * 
 */

public class InstructionProcessorTest {
    InstructionProcessor instructionProcessor = new InstructionProcessor();

    @Test
    public void testReceiveMultipleLinesDequeue() throws Exception {
	System.out.println(":::Tests for Dequeue:::");
	String inputMessage = "A MZ89 1 50 20160305T10:04:56.012Z" + "\r\nD MZ80 2 90 20150305T10:04:56.012Z"
		+ "\r\nC MZ87 3 70 20140305T10:04:56.012Z" + "\r\nB MZ87 4 70 20100305T10:04:56.012Z"
		+ "\r\nD MZ87 4 70 20100305T10:04:56.012Z" + "\r\nA MZ87 4 70 20100305T10:04:56.012Z";
	InstructionMessage dequedMessage = null;
	instructionProcessor.receive(inputMessage);
	InstructionQueue queue = instructionProcessor.getQueue();
	dequedMessage = queue.dequeue();
	Assert.assertEquals("A", dequedMessage.getInstructionType());
	System.out.println("\nDequeued Message :" + dequedMessage);
	dequedMessage = queue.dequeue();
	Assert.assertEquals("A", dequedMessage.getInstructionType());
	System.out.println("Dequeued Message :" + dequedMessage);
	dequedMessage = queue.dequeue();
	Assert.assertEquals("B", dequedMessage.getInstructionType());
	System.out.println("Dequeued Message :" + dequedMessage);
	dequedMessage = queue.dequeue();
	Assert.assertEquals("D", dequedMessage.getInstructionType());
	System.out.println("Dequeued Message :" + dequedMessage);
	dequedMessage = queue.dequeue();
	Assert.assertEquals("C", dequedMessage.getInstructionType());
	System.out.println("Dequeued Message :" + dequedMessage);
	dequedMessage = queue.dequeue();
	Assert.assertEquals("D", dequedMessage.getInstructionType());
	System.out.println("Dequeued Message :" + dequedMessage);
	Assert.assertTrue(queue.isEmpty());
	System.out.println("Is Queue Empty? " + queue.isEmpty());

    }

    @Test
    public void testReceiveMultipleLinesPeek() throws Exception {
	System.out.println(":::Tests for Peek:::");
	String inputMessage = "D MZ89 1 50 20160305T10:04:56.012Z" + "\r\nA MZ80 2 90 20150305T10:04:56.012Z"
		+ "\r\nC MZ87 3 70 20140305T10:04:56.012Z" + "\r\nB MZ87 4 70 20100305T10:04:56.012Z"
		+ "\r\nA MZ87 4 70 20100305T10:04:56.012Z" + "\r\nB MZ87 4 70 20100305T10:04:56.012Z";
	InstructionMessage peekMessage = null;
	instructionProcessor.receive(inputMessage);
	InstructionQueue queue = instructionProcessor.getQueue();
	peekMessage = queue.peek();
	Assert.assertEquals("MZ80", peekMessage.getProductCode());
	Assert.assertEquals(6, queue.count());
	System.out.println("\nPeek Message :" + peekMessage);
	System.out.println("Size of queue after peek is? " + queue.count());

    }

    @Test(expected = Exception.class)
    public void testReceiveInvalidQuantity() throws Exception {
	String inputMessage = "A MZ89 0 0 20150305T10:04:56.012Z";
	instructionProcessor.receive(inputMessage);

    }

    @Test(expected = Exception.class)
    public void testReceiveInvalidProductCode() throws Exception {
	String inputMessage = "A MZ890 0 0 20150305T10:04:56.012Z";
	instructionProcessor.receive(inputMessage);

    }

    @Test(expected = Exception.class)
    public void testReceiveInvalidInstructionType() throws Exception {
	String inputMessage = "F MZ890 40 560 20150305T10:04:56.012Z";
	instructionProcessor.receive(inputMessage);

    }

    @Test(expected = Exception.class)
    public void testReceiveInvalidUom() throws Exception {
	String inputMessage = "A MZ89 40 259 20150305T10:04:56.012Z";
	instructionProcessor.receive(inputMessage);

    }

    @Test(expected = Exception.class)
    public void testReceiveInvalidTimestamp() throws Exception {
	String inputMessage = "A MZ89 40 255 19560305T10:04:56.012Z";
	instructionProcessor.receive(inputMessage);

    }

    @Test(expected = ParseException.class)
    public void testReceiveInvalidDateFormat() throws Exception {
	String inputMessage = "A MZ89 40 255 0305T10:04:56.012Z";
	instructionProcessor.receive(inputMessage);

    }
}
