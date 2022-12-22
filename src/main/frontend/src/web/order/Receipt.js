import { useState } from "react";
import Modal from "web/admin/modal";
import styles from "css/order/receipt.module.scss";
function Receipt({ openModal, closeModal,orderId }) {

    return (
        <>
            <div className={styles.wrap}>
                <Modal closeModal={closeModal}>
                    <div className={styles.title}>
                        <span>
                            감사합니다.
                        </span>
                        <span>
                            주문이 완료되었습니다.
                        </span>
                    </div>
                    <div>
                        <div className={styles.bodyText}>
                            <div>
                                <p>
                                    주문번호
                                </p>
                                <h4>
                                    {orderId}
                                </h4>
                            </div>
                        </div>
                        <div className={styles.bottomBox}>
                            <div>
                                <span>메뉴가 준비되면 </span>
                                <span className={styles.emphasis}>주문번호 호출모니터</span>
                                <span>로 안내해드리겠습니다.</span>
                            </div>
                        </div>
                    </div>
                </Modal>
            </div>
        </>
    )
}

export default Receipt;