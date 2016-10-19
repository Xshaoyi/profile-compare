package yi.shao.webdriver.model;

public class CheckBoxRecord {
	private String fieldName;
	private String fieldType;
	private boolean isReadAccess;
	private boolean isEditAccess;
	private boolean isUpdate;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public boolean getIsReadAccess() {
		return isReadAccess;
	}
	public void setIsReadAccess(Boolean isReadAccess) {
		this.isReadAccess = isReadAccess;
	}
	public boolean getIsEditAccess() {
		return isEditAccess;
	}
	public void setIsEditAccess(Boolean isEditAccess) {
		this.isEditAccess = isEditAccess;
	}
	@Override
	public String toString() {
		return "CheckBoxRecord [fieldName=" + fieldName + ", fieldType=" + fieldType + ", isReadAccess=" + isReadAccess
				+ ", isEditAccess=" + isEditAccess + "]";
	}
	public boolean getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}
	
}
