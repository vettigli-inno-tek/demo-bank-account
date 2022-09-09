package it.innotek.demo.bankaccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.innotek.demo.bankaccount.model.transaction.TransactionHistory;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long>{

	@Query(value = "SELECT UT.id FROM USER_TRANSACTION UT WHERE UT.accountId = :accountId AND UT.transactionId IN (:transactionIds)")
	List<Long> findAllByAccountIdAndTransactionIds(@Param("accountId") long accountId, @Param("transactionIds") List<String> transactionIds);


}
