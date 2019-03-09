package myThread_Lynda;

public class HelloThread extends Thread {

	@Override
	public void run() {
		super.run();
		System.out.println(
				"hello from " + Thread.currentThread().getName() + " a thread created by extending Thread class");

	}
}
