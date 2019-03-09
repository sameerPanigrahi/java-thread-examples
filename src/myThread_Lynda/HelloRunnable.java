package myThread_Lynda;

public class HelloRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("hello from " + Thread.currentThread().getName()
				+ " a thread created by implementing Runnable Interface");

	}

}
