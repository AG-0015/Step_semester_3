interface NotificationChannel {
    void send(String message);
}

class EmailChannel implements NotificationChannel {
    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

class SMSChannel implements NotificationChannel {
    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

class Notice {
    private String title;
    private String message;

    public Notice(String title, String message) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Notice title cannot be blank");
        }

        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Notice message cannot be blank");
        }

        this.title = title;
        this.message = message;
    }

    public String getFullMessage() {
        return title + ": " + message;
    }
}

class NoticeBoard {
    private NotificationChannel channel;

    public NoticeBoard(NotificationChannel channel) {
        this.channel = channel;
    }

    public void broadcast(Notice notice) {
        channel.send(notice.getFullMessage());
    }
}

public class CampusNoticeBroadcaster {
    public static void main(String[] args) {
        Notice notice = new Notice(
                "Campus Event",
                "Coding contest starts at 10 AM"
        );

        NoticeBoard emailBoard =
                new NoticeBoard(new EmailChannel());

        NoticeBoard smsBoard =
                new NoticeBoard(new SMSChannel());

        emailBoard.broadcast(notice);
        smsBoard.broadcast(notice);
    }
}