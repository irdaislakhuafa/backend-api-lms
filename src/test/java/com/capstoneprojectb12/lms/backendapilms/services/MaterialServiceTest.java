package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.MaterialRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Tag(value = "materialServiceTest")
public class MaterialServiceTest {
	
	private final LocalDateTime deadline = LocalDateTime.now();
	private final Material material = Material.builder().id("id").classes(ClassServiceTest.classEntity).title("material title").content("material content").topic(null).videoUri("url").fileUrl("url").deadline(deadline).point(100).category(null).build();
	@Autowired
	private MaterialService materialService;
	@MockBean
	private MaterialRepository materialRepository;
	@MockBean
	private ClassService classService;
	
	@Test
	public void testNotNull() {
		assertNotNull(materialRepository);
	}
	
	@Test
	public void testSave() {
//        success
		when(this.materialRepository.save(any(Material.class))).thenReturn(material);
		var result = this.materialService.save(material);
		assertTrue(result.isPresent());
		assertEquals(material.getContent(), result.get().getContent());
		assertEquals(deadline, result.get().getDeadline());

//        failed
		when(this.materialRepository.save(any(Material.class))).thenReturn(null);
		result = this.materialService.save(material);
		assertFalse(result.isPresent());
		assertThrows(NoSuchElementException.class, () -> this.materialService.save(material).get());
	}
	
	@Test
	public void testToEntity() {
		final MaterialNew materialNew = MaterialNew.builder()
				.classId("id")
				.category("material category")
				.content("material content")
				.deadline(deadline)
				.point(100)
//				.file(null) // TODO: create file service first
//				.video(null) // TODO: create file service first
				.title("material title")
				.topicId("topicId")
				.build();
//		success
		when(this.classService.findById(anyString())).thenReturn(Optional.ofNullable(ClassServiceTest.classEntity));
		var result = this.materialService.toEntity(materialNew);
		assertNotNull(result);
		assertEquals(materialNew.getClassId(), result.getClasses().getId());
//		assertEquals(materialNew.getCategory(), result.getCategory()); // TODO: create category repo/service first
//		assertEquals(materialNew.getTopicId(), result.getTopic().getId()); // TODO: create topic repo/service first
		assertEquals(materialNew.getContent(), result.getContent());
		assertEquals(materialNew.getDeadline(), result.getDeadline());
		assertEquals(materialNew.getPoint(), result.getPoint());
		assertEquals(materialNew.getTitle(), result.getTitle());

//		failed
		when(this.classService.findById(anyString())).thenReturn(Optional.empty());
		assertThrows(ClassNotFoundException.class, () -> this.materialService.toEntity(materialNew));
	}
}
