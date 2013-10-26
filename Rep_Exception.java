package Project;

class Rep_Exception extends Exception {

	private String error_msg;

	public Rep_Exception(String msg) {
		error_msg = msg;
	}

	public String get_msg() {
		return error_msg;
	}
}
