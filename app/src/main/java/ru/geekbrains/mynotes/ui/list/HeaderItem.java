package ru.geekbrains.mynotes.ui.list;

public class HeaderItem implements AdapterItem {

    private final String title;

    public HeaderItem(String title) {
        this.title = title;
    }

    @Override
    public String getUniqueTag() {
        return "HeaderItem" + title;
    }

    public String getTitle() {
        return title;
    }
}