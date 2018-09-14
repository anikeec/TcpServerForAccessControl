package com.apu.TcpServerForAccessControl.utils.database;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlDB.entity.AccessMessage;

@Component
public class AccessMessageInsertListener implements PostInsertEventListener {

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {
        if(event.getEntity() instanceof AccessMessage) {
            AccessMessage insertedMessage = (AccessMessage)event.getEntity();
            System.out.print(insertedMessage);
        }
        
    }

}
