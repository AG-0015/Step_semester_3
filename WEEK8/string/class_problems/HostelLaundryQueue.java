import java.util.LinkedList;
import java.util.Queue;

class LaundryItem {
    private String studentName;
    private String clothType;

    public LaundryItem(String studentName, String clothType) {
        this.studentName = studentName;
        this.clothType = clothType;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getClothType() {
        return clothType;
    }

    @Override
    public String toString() {
        return studentName + " - " + clothType;
    }
}

class LaundryQueue {
    private Queue<LaundryItem> queue = new LinkedList<>();

    public void addItem(LaundryItem item) {
        queue.offer(item);
    }

    public LaundryItem processNext() {
        return queue.poll();
    }

    public int getQueueSize() {
        return queue.size();
    }
}

public class HostelLaundryQueue {
    public static void main(String[] args) {
        LaundryQueue laundryQueue = new LaundryQueue();

        laundryQueue.addItem(new LaundryItem("Arun", "Shirt"));
        laundryQueue.addItem(new LaundryItem("Priya", "Bedsheet"));
        laundryQueue.addItem(new LaundryItem("Rahul", "Jeans"));

        System.out.println("Queue Size: " + laundryQueue.getQueueSize());

        System.out.println("Processing: " + laundryQueue.processNext());
        System.out.println("Processing: " + laundryQueue.processNext());

        System.out.println("Remaining: " + laundryQueue.getQueueSize());
    }
}