package com.shopservice.com.shopservice.assemblers;

import com.shopservice.assemblers.CategoryAssembler;
import com.shopservice.dao.CategoryRepository;
import com.shopservice.dao.ProductEntryRepository;
import com.shopservice.domain.Category;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryAssemblerTest {

    CategoryAssembler assembler;

    ProductEntryRepository mockedProductRepository;
    CategoryRepository mockedCategoryRepository;

    @Before
    public void setUp() throws Exception {
        mockedProductRepository = mock(ProductEntryRepository.class);
        mockedCategoryRepository = mock(CategoryRepository.class);
        assembler = new CategoryAssembler( mockedProductRepository, mockedCategoryRepository );
    }

    @Test
    public void testGetOneCategoryWithEmptyCountPerCategory() throws Exception {
        when(mockedProductRepository.getCountPerCategory("1","1")).thenReturn(new HashMap<String, Integer>());

        when(mockedCategoryRepository.getCategories()).thenReturn(Arrays.asList( new Category("1") ) );

        List<Category> categories = new ArrayList<Category>(assembler.getCategories("client1","group1").categories);

        assertThat(categories).isNotEmpty();
        assertThat(categories.get(0).count).isEqualTo(0);
    }

    @Test
    public void testGetOneCategoryWithOneCountPerCategory() throws Exception {
        when(mockedProductRepository.getCountPerCategory("client1","group1")).thenReturn(
                new MapConstructor<String,Integer>().with("1",10).getMap()
        );

        when(mockedCategoryRepository.getCategories()).thenReturn(Arrays.asList( new Category("1") ) );

        List<Category> categories = new ArrayList<Category>(assembler.getCategories("client1","group1").categories);

        assertThat(categories).isNotEmpty();
        assertThat(categories.get(0).count).isEqualTo(10);
    }

    private static class MapConstructor<T,A>
    {
        Map<T,A> map = new HashMap<T, A>();

        public MapConstructor with(T key, A value)
        {
            map.put(key, value);

            return this;
        }

        public Map<T,A> getMap()
        {
            return map;
        }
    }
}
