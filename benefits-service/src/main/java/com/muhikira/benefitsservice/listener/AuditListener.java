package com.muhikira.benefitsservice.listener;

import com.muhikira.benefitsservice.model.audit.Audit;
import com.muhikira.benefitsservice.model.audit.Auditable;
import com.muhikira.benefitsservice.util.SecurityUtils;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {

  @PrePersist
  public void setCreationAuditFields(Object entity) {
    if (entity instanceof Auditable auditable) {  // Pattern matching for instanceof
      Audit audit = auditable.getAudit();

      // Initialize Audit if it's null
      if (audit == null) {
        audit = new Audit();
        auditable.setAudit(audit);
      }

      audit.setCreatedBy(SecurityUtils.getCurrentUser());
      audit.setCreateTimestamp(LocalDateTime.now());
      audit.setCreateModule("BenefitService");
    }
  }

  @PreUpdate
  public void setUpdateAuditFields(Object entity) {
    if (entity instanceof Auditable auditable) {  // Pattern matching for instanceof
      Audit audit = auditable.getAudit();

      // Initialize Audit if it's null (precautionary, should already be set)
      if (audit == null) {
        audit = new Audit();
        auditable.setAudit(audit);
      }

      audit.setUpdatedBy(SecurityUtils.getCurrentUser());
      audit.setUpdateTimestamp(LocalDateTime.now());
      audit.setUpdateModule("BenefitService");
    }
  }
}
