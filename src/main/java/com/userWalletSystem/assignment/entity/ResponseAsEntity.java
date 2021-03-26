package com.userWalletSystem.assignment.entity;

public class ResponseAsEntity {
	
	private String status;
    private String description;
    private Object data;
    
    public ResponseAsEntity() {
    	
    }
    
	public ResponseAsEntity(String status, String description, Object data) {
		super();
		this.status = status;
		this.description = description;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseEntity [status=" + status + ", description=" + description + ", data=" + data + "]";
	}
    
    

}
