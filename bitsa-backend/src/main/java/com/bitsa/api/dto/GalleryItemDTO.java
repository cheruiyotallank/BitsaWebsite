package com.bitsa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GalleryItemDTO - Safe gallery item response object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryItemDTO {
    private Long id;
    private String filename;
    private String url;
    private String caption;
    private UserDTO uploadedBy;
}
