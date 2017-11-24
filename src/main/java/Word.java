import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Word.
 */
@Entity
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word")
    private String word;

    @Column(name = "word_count")
    private Long wordCount;

    @Column(name = "page_id")
    private Long pageId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public Word word(String word) {
        this.word = word;
        return this;
    }

    public Word() {

    }

    public Word(String word, Long count) {
        this.word = word;
        this.wordCount = count;
    }
    public void setWord(String word) {
        this.word = word;
    }

    public Long getWordCount() {
        return wordCount;
    }

    public Word count(Long count) {
        this.wordCount = count;
        return this;
    }

    public void setWordCount(Long count) {
        this.wordCount = count;
    }

    public Long getPageId() {
        return pageId;
    }

    public Word page(Long pageId) {
        this.pageId = pageId;
        return this;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word = (Word) o;
        if (word.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), word.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Word{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            ", count='" + getWordCount() + "'" +
            ", pageId='" + getPageId() + "'" +
            "}";
    }
}
