package com.playbook.error;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

/**
 * View Model for transferring error message with a list of field errors.
 */
public class ErrorVM implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String message;
	private final String description;

	private List<FieldErrorVM> fieldErrors;

	public ErrorVM(String message) {
		this(message, null);
	}

	public ErrorVM(String message, String description) {
		this.message = message;
		this.description = description;
	}

	public ErrorVM(String message, String description, List<FieldErrorVM> fieldErrors) {
		this.message = message;
        this.description = description;
        if (Objects.nonNull(fieldErrors)) {
            this.fieldErrors = Lists.newArrayList(fieldErrors);
        }
	}

	public void add(String objectName, String field, String message) {
		if (fieldErrors == null) {
			fieldErrors = new ArrayList<>();
		}
		fieldErrors.add(new FieldErrorVM(objectName, field, message));
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

    public List<FieldErrorVM> getFieldErrors() {
        if (Objects.nonNull(fieldErrors)) {
            return Lists.newArrayList(fieldErrors);
        } else {
            return null;
        }
    }

	@Override
	public String toString() {
		return this.message;
	}
}
