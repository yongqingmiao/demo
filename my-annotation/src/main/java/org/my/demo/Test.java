package org.my.demo;

public class Test {
	public static void main(String[] args) {
		User user = new User("张三123123", "xdemo.org", "252878950@qqcom");
		try {
			ValidateService.valid(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
