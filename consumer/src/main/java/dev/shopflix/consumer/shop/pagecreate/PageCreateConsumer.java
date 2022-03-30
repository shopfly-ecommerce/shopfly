/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.pagecreate;

import dev.shopflix.consumer.core.event.PageCreateEvent;
import dev.shopflix.consumer.shop.pagecreate.service.PageCreator;
import dev.shopflix.core.client.goods.GoodsClient;
import dev.shopflix.core.client.system.StaticsPageHelpClient;
import dev.shopflix.core.pagecreate.model.PageCreateEnum;
import dev.shopflix.core.system.model.TaskProgressConstant;
import dev.shopflix.core.system.model.enums.ProgressEnum;
import dev.shopflix.core.system.model.vo.TaskProgress;
import dev.shopflix.core.system.service.ProgressManager;
import dev.shopflix.framework.logs.Debugger;
import dev.shopflix.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 静态页面创建
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/7/17 下午3:55
 */
@Service
public class PageCreateConsumer implements PageCreateEvent {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private PageCreator pageCreator;

    @Autowired
    private ProgressManager progressManager;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StaticsPageHelpClient staticsPageHelpClient;

    @Autowired
    private Debugger debugger;

    /**
     * 生成
     *
     * @param choosePages
     */
    @Override
    public void createPage(String[] choosePages) {
        debugger.log("开始生成静态页面：", StringUtil.arrayToString(choosePages, ","));

        if (choosePages.length >= 3) {
            int goodsCount = 0, helpCount = 0, indexCount = 2;
            try {
                goodsCount = goodsClient.queryGoodsCount();
                helpCount = staticsPageHelpClient.count();
                progressManager.taskBegin(TaskProgressConstant.PAGE_CREATE,helpCount + goodsCount + indexCount);
                pageCreator.createAll();
                this.successMessage();
            } catch (Exception e) {
                progressManager.remove(TaskProgressConstant.PAGE_CREATE);
                this.logger.error("静态页面异常：", e);
            }
        } else {
            int count = 0;
            int goodsCount = 0,helpCount = 0;
            for (String choose : choosePages) {
                if (choose.equals(PageCreateEnum.GOODS.name())) {
                    goodsCount = goodsClient.queryGoodsCount();
                    count += goodsCount;
                }
                if (choose.equals(PageCreateEnum.HELP.name())) {
                    helpCount = staticsPageHelpClient.count();
                    count += helpCount;
                }
                if (choose.equals(PageCreateEnum.INDEX.name())) {
                    count += 2;
                }
            }
            this.createMessage(count);

            for (String choose : choosePages) {
                try {
                    if (choose.equals(PageCreateEnum.GOODS.name()) && goodsCount > 0) {
                        this.pageCreator.createGoods();
                    }
                    if (choose.equals(PageCreateEnum.HELP.name()) && helpCount > 0) {
                        this.pageCreator.createHelp();
                    }
                    if (choose.equals(PageCreateEnum.INDEX.name())) {
                        this.pageCreator.createIndex();
                    }
                    this.successMessage();
                } catch (Exception e) {
                    this.errorMessage("静态页面异常：" + e.getMessage());
                    this.logger.error("静态页面异常：", e);
                }
            }
        }
    }

    private void createMessage(Integer total) {
        TaskProgress tk = new TaskProgress(total);
        tk.setId(TaskProgressConstant.PAGE_CREATE);
        progressManager.putProgress(TaskProgressConstant.PAGE_CREATE, tk);
    }


    private void successMessage() {
        TaskProgress tk = progressManager.getProgress(TaskProgressConstant.PAGE_CREATE);
        if(tk == null ){
            tk = new TaskProgress(0);
            tk.step("静态页生成停止");
        }else{
            tk.step("静态页生成完成");
        }

        tk.success();
        progressManager.putProgress(TaskProgressConstant.PAGE_CREATE, tk);
    }

    private void errorMessage(String message) {
        TaskProgress tk = progressManager.getProgress(TaskProgressConstant.PAGE_CREATE);
        tk.setTaskStatus(ProgressEnum.EXCEPTION.name());
        tk.setMessage(message);
        progressManager.putProgress(TaskProgressConstant.PAGE_CREATE, tk);
    }


}