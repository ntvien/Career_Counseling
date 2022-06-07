package group12.career_counseling.web_service.mongodb.generic;

import group12.career_counseling.web_service.mongodb.operator.MongoDBOperator;
import lombok.NonNull;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface IGenericRepository<T extends PO> {

    MongoDBOperator<T> getMongoDBOperator();

    Optional<Long> count(@NonNull Document query);

    Optional<T> getById(@NonNull String _id);

    Optional<T> getById(@NonNull ObjectId _id);

    Optional<T> getByQuery(@NonNull Document query);

    Optional<List<T>> getMany(@NonNull Document query, @NonNull Document sort, int offset, int limit);

    Optional<List<T>> getMany(@NonNull Document query, @NonNull Document sort, Document projection, int offset, int limit);

    Optional<List<T>> getManyByPage(@NonNull Document query, @NonNull Document sort, int page, int size);

    Optional<List<T>> getAll(@NonNull Document query, @NonNull Document sort);

    Optional<T> insert(@NonNull T t);

    Optional<List<T>> insert(@NonNull List<T> t);

    Optional<Boolean> update(@NonNull String _id, @NonNull T t);

    Optional<Boolean> update(@NonNull Document query, @NonNull T t);

    Optional<Boolean> updateMany(@NonNull Document query, @NonNull Document data);

    Optional<Boolean> delete(@NonNull String _id);

    Optional<Boolean> deleteMany(@NonNull Document query);

    Optional<Boolean> addToSet(@NonNull Document query, @NonNull Document data);

}
