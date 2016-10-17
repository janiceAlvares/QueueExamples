/**
 * 
 */
package org.tp.instruction.processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;

import org.tp.instruction.message.InstructionMessage;
import org.tp.instruction.message.MessageReceiver;
import org.tp.instruction.queue.InstructionQueue;

/**
 * Instruction processor which builds the {@link InstructionMessage } and adds
 * valid messages to the {@link InstructionQueue}.
 * 
 * @author Janice Alvares
 * 
 */
public class InstructionProcessor implements MessageReceiver {

    Logger logger = Logger.getLogger(InstructionProcessor.class.getName());
    InstructionQueue queue = new InstructionQueue();

    public void receive(String message) throws Exception {
	try {
	    String input[] = message.split("\\r?\\n");
	    for (String messageline : input) {

		InstructionMessage instructionMessage = createInstructionMessage(messageline);
		System.out.println("Enqueued Message :" + instructionMessage.toString());
		queue.enqueue(instructionMessage);
	    }
	} catch (Exception e) {

	    System.out.println("Exeption while adding message to queue:" + e.getMessage());
	    throw e;
	}

    }

    private InstructionMessage createInstructionMessage(String messageline) throws Exception {

	InstructionMessage instructionMessage = new InstructionMessage();
	try {
	    String instructions[] = messageline.split("\\s+");
	    String instructionType = instructions[0];
	    String productCode = instructions[1];
	    Integer quantity = Integer.parseInt(instructions[2]);
	    Integer uom = Integer.parseInt(instructions[3]);
	    Date date = new SimpleDateFormat("yyyyMMdd'T'HH:mm:ss.SSS'Z'").parse(instructions[4]);
	    Long timestamp = date.toInstant().toEpochMilli();
	    if (null != instructionType && instructionType.matches("[A-D]")) {
		instructionMessage.setInstructionType(instructionType);
	    } else {
		throw new Exception("Invalid InstructionType");
	    }

	    if (null != productCode && productCode.matches("[A-Z]{2}[0-9]{2}")) {
		instructionMessage.setProductCode(productCode);
	    } else {
		throw new Exception("Invalid ProductCode");
	    }
	    if (null != quantity && quantity > 0) {
		instructionMessage.setQuantity(quantity);
	    } else {
		throw new Exception("Invalid Quantity");
	    }
	    if (0 <= uom && uom < 256) {
		instructionMessage.setUom(uom);
	    } else {
		throw new Exception("Invalid Uom");
	    }
	    if (0 < timestamp && timestamp <= Instant.now().toEpochMilli()) {
		instructionMessage.setTimestamp(timestamp);
	    } else {
		throw new Exception("Invalid Timestamp");
	    }
	} catch (ParseException e) {
	    System.out.println("Error while parsing timestamp to SimpleDate");
	    throw e;
	} catch (Exception e) {
	    throw e;
	}
	return instructionMessage;
    }

    public InstructionQueue getQueue() {
	return queue;
    }

}
