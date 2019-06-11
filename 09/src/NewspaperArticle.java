/*
 * SD2x Homework #9
 * This class represents a newspaper article.
 * Refactor the code according to the suggestions in the assignment description.
 */
import java.util.*;

public class NewspaperArticle extends Document {
    private Set<String> editors;
    private String newspaper;
    private int startPage;
    private int endPage;

    public NewspaperArticle(String title, String author, Set<String> editors, String newspaper, Date date, String city, String state, String postCode) {
        super(title, author, date, city, state, postCode);
        this.editors = editors;
        this.newspaper = newspaper;
    }

    public Set<String> getEditors() {
        return editors;
    }

    public String getNewspaper() {
        return newspaper;
    }

    public boolean sameNewspaper(NewspaperArticle article) {
        return this.newspaper.equals(article.newspaper);
    }

    public int numEditors(){
        return editors.size();
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public int numPages(){
        return endPage - startPage + 1;
    }

    public Set<String> commonEditors(NewspaperArticle article){
        Set<String> sameEditors = new HashSet<String>();
        for(String ed : article.editors){
            if(this.editors.contains(ed)){
                sameEditors.add(ed);
            }
        }
        return sameEditors;
    }

}
