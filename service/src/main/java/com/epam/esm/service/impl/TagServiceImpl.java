package com.epam.esm.service.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.utils.converter.TagConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagConverter tagConverter;

    @Override
    public TagDto findById(Long id) {
        return tagConverter.toDto(tagRepository.findById(id));
    }

    @Override
    public List<TagDto> findAll() {
        return tagRepository.findAll().stream()
                .map(tagConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long insert(TagDto tagDto) {
        return tagRepository.insert(tagConverter.toEntity(tagDto), false);
    }

    @Override
    public void update(TagDto tagDto) {
        tagRepository.findById(tagDto.getId());
        Tag tag = tagConverter.toEntity(tagDto);
        tagRepository.update(tag);
    }

    @Override
    public void delete(Long id) {
        tagRepository.findById(id);
        tagRepository.delete(id);
    }
}
