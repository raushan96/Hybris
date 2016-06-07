package de.andre.utils.idgen;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import java.sql.SQLException;

public class IdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    private static final String selectSpaceSQL = "SELECT seed, batch_size FROM hybris.he_number_space WHERE space_name_id = ?";
    private static final String updateSeedSQL = "UPDATE hybris.he_number_space SET seed = ? WHERE space_name_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final String orderIdSpaceName;

    private IdSpace orderIdSpace;

    public IdGenerator(final JdbcTemplate jdbcTemplate, final String orderIdSpaceName) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderIdSpaceName = orderIdSpaceName;
    }

    public void setUpDefaultSpace() throws SQLException {
        // init credit and deposit number spaces
        orderIdSpace = querySpace(orderIdSpaceName);

        logger.debug("Order space initialized => {}", orderIdSpace);
        //increase seed by batch size
        reserveSeed(orderIdSpace);
    }

    private IdSpace querySpace(final String spaceName) {
        return jdbcTemplate.queryForObject(
                selectSpaceSQL,
                (rs, rowNum) -> new IdSpace(spaceName, rs.getLong(1), rs.getLong(2)),
                spaceName);
    }

    private void reserveSeed(final IdSpace idSpace) {
        jdbcTemplate.update(
                updateSeedSQL,
                idSpace.getSeed() + idSpace.getBatchSize(),
                idSpace.getName());
    }

    public String generateOrderStringNumber() {
        return generateStringId(orderIdSpace);
    }

    public String generateStringId(final IdSpace idSpace) {
        Assert.notNull(idSpace);
        final String stringId;

        synchronized (idSpace) {
            final long longId = idSpace.getSequence().getAndIncrement();
            stringId = StringUtils.leftPad(String.valueOf(longId), 4, "0");
            if (longId == idSpace.getThreshold()) {
                reserveSeed(idSpace);
                idSpace.setSeed(idSpace.getSeed() + idSpace.getBatchSize());
                idSpace.setThreshold(idSpace.getSeed() + idSpace.getBatchSize() - 1);
            }
            logger.debug("Generated {} id.", stringId);
        }

        return stringId;
    }

    public IdSpace getOrderIdSpace() {
        return orderIdSpace;
    }
}
