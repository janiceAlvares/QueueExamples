/**
 * 
 */
package org.tp.instruction.message;

import java.io.Serializable;

/**
 * This class represents instruction messages received as a string.
 * 
 * 
 * @author Janice Alvares
 * 
 */
public class InstructionMessage implements Serializable, Comparable<InstructionMessage> {

    private static final long serialVersionUID = 2293002098489272471L;

    private String instructionType;
    private String productCode;
    private Integer quantity;
    private Integer uom;
    private Long timestamp;
    private Long insertionTime = System.nanoTime();

    public Long getInsertionTime() {
	return insertionTime;
    }

    public void setInsertionTime(Long insertionTime) {
	this.insertionTime = insertionTime;
    }

    public String getInstructionType() {
	return instructionType;
    }

    public void setInstructionType(String instructionType) {
	this.instructionType = instructionType;
    }

    public String getProductCode() {
	return productCode;
    }

    public void setProductCode(String productCode) {
	this.productCode = productCode;
    }

    public Integer getQuantity() {
	return quantity;
    }

    public void setQuantity(Integer quantity) {
	this.quantity = quantity;
    }

    public Integer getUom() {
	return uom;
    }

    public void setUom(Integer uom) {
	this.uom = uom;
    }

    public Long getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(Long timestamp) {
	this.timestamp = timestamp;
    }

    @Override
    public String toString() {
	return "InstructionMessage [instructionType=" + instructionType + ", productCode=" + productCode + ", quantity="
		+ quantity + ", uom=" + uom + ", timestamp=" + timestamp + ", insertionTime=" + insertionTime + "]";
    }

    public int compareTo(InstructionMessage o) {
	// Maintain FIFO order for equal and elements with same priority.
	if (this.getInstructionType().equals(o.getInstructionType())
		|| (this.getInstructionType().matches("[C-D]") && o.getInstructionType().matches("[C-D]"))) {

	    if (this.insertionTime < o.insertionTime) {
		return -1;
	    } else {
		return 1;
	    }
	} else {

	    return this.getInstructionType().compareTo(o.getInstructionType());
	}
    }
}
