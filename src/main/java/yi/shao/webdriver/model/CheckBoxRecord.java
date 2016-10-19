package yi.shao.webdriver.model;

public class CheckBoxRecord {
	private String fieldName;
	private String fieldType;
	private Boolean isReadAccess;
	private Boolean isEditAccess;
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
	public Boolean getIsReadAccess() {
		return isReadAccess;
	}
	public void setIsReadAccess(Boolean isReadAccess) {
		this.isReadAccess = isReadAccess;
	}
	public Boolean getIsEditAccess() {
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
	
}
