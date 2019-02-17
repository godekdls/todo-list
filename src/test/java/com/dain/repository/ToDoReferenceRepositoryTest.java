package com.dain.repository;

import com.dain.MockToDoFactory;
import com.dain.TestConfig;
import com.dain.model.ToDo;
import com.dain.model.ToDoReference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ToDoReferenceRepositoryTest extends TestConfig {

    @Autowired
    private ToDoReferenceRepository referenceRepository;

    @Test
    public void 참조된id로_검색해서_참조관계를_삭제할수있다() {
        // given
        ToDoReference reference = new ToDoReference();
        reference.setReferredId(100l);
        ToDo toDo = MockToDoFactory.getMockToDo();
        toDo.getReferences().add(reference);
        reference.setToDo(toDo);
        this.referenceRepository.save(reference);

        // when
        this.referenceRepository.deleteAllByReferredId(reference.getReferredId());

        // then
        assertThat(this.referenceRepository.findById(reference.getId()).isPresent(), is(false));
    }

}
