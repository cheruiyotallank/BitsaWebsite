package com.bitsa.api.repository;

import com.bitsa.api.model.GalleryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<GalleryItem, Long> {
}
