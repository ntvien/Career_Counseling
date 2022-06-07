package group12.career_counseling.web_service.mongodb.generic;

import group12.career_counseling.web_service.utils.QueryFactory;
import lombok.NonNull;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public abstract class GenericRepository<T extends PO> implements IGenericRepository<T> {

    @Override
    public Optional<Long> count(@NonNull Document query) {
        try {
            query.putIfAbsent("isDeleted", false);
            return Optional.of(getMongoDBOperator().count(query));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.of(0L);
    }

    @Override
    public Optional<T> getById(@NonNull String _id) {
        return this.getById(new ObjectId((_id)));
    }

    @Override
    public Optional<T> getById(@NonNull ObjectId _id) {
        try {
            Document query = new Document()
                    .append("_id", _id)
                    .append("isDeleted", false);
            return Optional.of(getMongoDBOperator().find(query, new Document()));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> getByQuery(@NonNull Document query) {
        try {
            query.putIfAbsent("isDeleted", false);

            return Optional.ofNullable(getMongoDBOperator().find(query, new Document()));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<T>> getMany(@NonNull Document query, @NonNull Document sort, int offset, int limit) {
        try {
            query.putIfAbsent("isDeleted", false);
            return Optional.of(getMongoDBOperator().findMany(query, sort, offset, limit));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<T>> getMany(@NonNull Document query, @NonNull Document sort, Document projection, int offset, int limit) {
        try {
            query.putIfAbsent("isDeleted", false);
            return Optional.of(getMongoDBOperator().findMany(query, sort, projection, offset, limit));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<T>> getManyByPage(@NonNull Document query, @NonNull Document sort, int page, int size) {
        try {
            query.putIfAbsent("isDeleted", false);
            return Optional.of(getMongoDBOperator().findMany(query, sort, (page - 1) * size, size));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<T>> getAll(@NonNull Document query, @NonNull Document sort) {
        try {
            query.putIfAbsent("isDeleted", false);
            return Optional.of(getMongoDBOperator().findMany(query, sort, 0, 0));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> insert(@NonNull T t) {
        try {
            if (t.getCreatedTime() == null || t.getCreatedTime() == 0)
                t.setCreatedTime(System.currentTimeMillis());
            t.setUpdatedTime(System.currentTimeMillis());
            return Optional.of(getMongoDBOperator().insert(t));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<T>> insert(@NonNull List<T> t) {
        try {
            return Optional.ofNullable(getMongoDBOperator().insert(t));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> update(@NonNull String _id, @NonNull T t) {
        try {
            Document query = new Document()
                    .append("_id", new ObjectId(_id));
            t.setUpdatedTime(System.currentTimeMillis());
            Document update = QueryFactory.buildQuerySet(t);
            return Optional.ofNullable(getMongoDBOperator().updateOne(query, update));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> update(@NonNull Document query, @NonNull T t) {
        try {
            t.setUpdatedTime(System.currentTimeMillis());
            Document update = QueryFactory.buildQuerySet(t);
            return Optional.ofNullable(getMongoDBOperator().updateOne(query, update));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> updateMany(@NonNull Document query, @NonNull Document data) {
        try {
            data.append("updatedDate", System.currentTimeMillis());
            return Optional.of(getMongoDBOperator().updateMany(query, data));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> delete(@NonNull String _id) {
        try {
            Document query = new Document()
                    .append("_id", new ObjectId(_id));
            return Optional.of(getMongoDBOperator().updateMany(query, new Document("isDeleted", true)));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> deleteMany(@NonNull Document query) {
        try {
            return Optional.of(getMongoDBOperator().updateMany(query, new Document("isDeleted", true)));

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> addToSet(@NonNull Document query, @NonNull Document data) {
        try {
            Document update = new Document()
                    .append("updatedDate", System.currentTimeMillis())
                    .append("$addToSet", data);
            return Optional.ofNullable(getMongoDBOperator().updateMany(query, update));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }
}
