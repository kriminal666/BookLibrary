package bookLibrary.interfaces;

public interface IMessage {
	
	/**
	 * Send message to the view
	 * @param action
	 * @param fail
	 * @param exception
	 */
	public void messageMaker(String action, boolean fail, String... msg);

}
