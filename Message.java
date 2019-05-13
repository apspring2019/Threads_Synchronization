public class Message
{
    private final String title;
    private final String text;

    public Message(String title, String text)
    {
        this.title = title;
        this.text = text;
    }

    public String getTitle()
    {
        return title;
    }

    public String getText()
    {
        return text;
    }

    @Override
    public String toString()
    {
        return String.format("%s%n%s", title, text);
    }
}
