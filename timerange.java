// open your connection
long start = System.currentTimeMillis();
// send request, wait for response (the simple socket calls are all blocking)
long end = System.currentTimeMillis();
System.out.println("Round trip response time = " + (end - start) + " millis");
