/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.distribution;

import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.model.vo.WithdrawApplyVO;
import cloud.shopfly.b2c.core.distribution.service.WithdrawManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * 分销提现控制器
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/24 下午5:11
 */
@Api(description = "分销提现控制器")
@RestController
@RequestMapping("/seller/distribution/withdraw")
public class WithdrawSellerController {
    @Autowired
    private WithdrawManager withdrawManager;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @ApiOperation("提现申请审核列表")
    @GetMapping(value = "/apply")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_size", value = "页码大小", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "uname", value = "会员名", required = false, paramType = "query", dataType = "String", allowMultiple = false),
            @ApiImplicitParam(name = "end_time", value = "结束时间", required = false, paramType = "query", dataType = "String", allowMultiple = false),
            @ApiImplicitParam(name = "start_time", value = "开始时间", required = false, paramType = "query", dataType = "String", allowMultiple = false),
            @ApiImplicitParam(name = "status", value = "状态 全部的话，不要传递参数即可 APPLY:申请中/VIA_AUDITING:审核成功/FAIL_AUDITING:审核失败/RANSFER_ACCOUNTS:已转账 ", required = false, paramType = "query", dataType = "String", allowMultiple = false)
    })
    public Page<WithdrawApplyVO> pageApply(@ApiIgnore String endTime, @ApiIgnore String startTime, @ApiIgnore String uname, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String status) {

        Map<String,String> map = new HashMap(4);
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        map.put("uname", uname);
        map.put("status",status);
        return withdrawManager.pageApply(pageNo, pageSize, map);
    }


    @PostMapping(value = "/auditing")
    @ApiOperation("审核提现申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apply_id", value = "申请id", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "audit_result", value = "申请结果 VIA_AUDITING：审核通过/FAIL_AUDITING：审核拒绝", required = false, paramType = "query", dataType = "String", allowMultiple = false),
    })
    public void auditing(@ApiIgnore int applyId, String remark, @ApiIgnore String auditResult) {
        try {

            this.withdrawManager.auditing(applyId, remark, auditResult);
        } catch (DistributionException e) {
            logger.error("提现审核异常：", e);
            throw e;
        } catch (Exception e) {
            logger.error("提现审核异常：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @ApiOperation("设为已转账")
    @PostMapping(value = "/account/end")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apply_id", value = "申请id", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "int", allowMultiple = false),
    })
    public void accountPaid(@ApiIgnore int applyId, String remark) {
        try {
            this.withdrawManager.transfer(applyId, remark);
        } catch (DistributionException e) {
            logger.error("设为已转账异常：", e);
            throw e;
        } catch (Exception e) {
            logger.error("设为已转账异常：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }

    }
}
