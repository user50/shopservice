package com.shopservice.dao;

import com.shopservice.LinkedEntryCondition;
import com.shopservice.domain.LinkedProductEntry;
import org.hibernate.Session;

import java.util.List;

import static com.shopservice.HibernateUtil.*;

public class HibernateLinkedProductEntryRepository implements LinkedProductEntryRepository{
    @Override
    public List<LinkedProductEntry> find(LinkedEntryCondition condition) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public LinkedProductEntry create(final LinkedProductEntry linkedProductEntry) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.save(linkedProductEntry);
            }
        });

        return linkedProductEntry;
    }

    @Override
    public LinkedProductEntry update(final LinkedProductEntry linkedProductEntry) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                session.update(linkedProductEntry);
            }
        });

        return linkedProductEntry;
    }

    @Override
    public void remove(final Integer linkedProductEntryId) {
        execute(new Update() {
            @Override
            public void execute(Session session) {
                LinkedProductEntry linkedProductEntry = (LinkedProductEntry) session.get(LinkedProductEntry.class, linkedProductEntryId);
                session.delete(linkedProductEntry);
            }
        });
    }
}
