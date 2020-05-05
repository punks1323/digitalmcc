package com.cluster.digital.database.entity.id_generators;

import com.cluster.digital.utils.MConstants;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.spi.QueryImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * @author pankaj
 * @version 1.0
 * @since 2019-06-27
 */
public class ApplicationIdGenerator
        implements IdentifierGenerator, Configurable {

    private String prefix;
    private String leadCount;

    @Override
    public Serializable generate(
            SharedSessionContractImplementor session, Object obj)
            throws HibernateException {

        String sql = String.format("select %s from %s",
                session.getEntityPersister(obj.getClass().getName(), obj)
                        .getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

        QueryImplementor<String> query = session.createQuery(sql, String.class);
        Stream<String> ids = query.stream();

        long max = ids.map(o -> o.replace(prefix, ""))
                .mapToLong(Long::parseLong)
                .max()
                .orElse(0L);

        return prefix + String.format("%0" + leadCount + "d", (max + 1));
    }

    @Override
    public void configure(Type type, Properties properties,
                          ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty(MConstants.ENTITY_ID.PREFIX);
        leadCount = properties.getProperty(MConstants.ENTITY_ID.LEAD_ZERO_COUNT);
    }
}