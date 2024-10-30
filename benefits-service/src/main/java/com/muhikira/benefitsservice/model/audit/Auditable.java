package com.muhikira.benefitsservice.model.audit;

import java.io.Serializable;

public interface Auditable extends Serializable {
    Audit getAudit();

    void setAudit(Audit audit);
}
