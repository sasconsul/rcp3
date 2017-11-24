import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class WordCountService {
    protected EntityManager em;

    public WordCountService(EntityManager em) {
        this.em = em;
    }

    public Page createPage(String url) {
        Page page = new Page();
        page.setUrl(url);
        em.persist(page);
        return page;
    }

    public void removePage(int id) {
        Page emp = findPage(id);
        if (emp != null) {
            em.remove(emp);
        }
    }


    public Page findPage(int id) {
        return em.find(Page.class, id);
    }

    public Collection<Page> findAllPages() {
        Query query = em.createQuery("SELECT e FROM Page e");
        return (Collection<Page>) query.getResultList();
    }
}
