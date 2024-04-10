
package com.posta.crm.repository.canvas;

import com.posta.crm.entity.canvas.CanvasModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CanvasModelRepository  extends JpaRepository<CanvasModel, Long>{
    @Query("SELECT p FROM CanvasModel p WHERE p.client.id = ?1")
    Optional<CanvasModel> findCanvasModelByClientId(Long clientId);
    
}
