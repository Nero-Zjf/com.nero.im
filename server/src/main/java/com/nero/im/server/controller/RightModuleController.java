package com.nero.im.server.controller;

import com.nero.im.server.domain.RequestResult;
import com.nero.im.server.domain.rm.RightModule;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 权限模块控制器
 *
 * @author Nero  2018-06-25 10:14:00
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/rightmodule")
public class RightModuleController {

    private List<RightModule> rightModuleList = new ArrayList<RightModule>() {
        {
            add(new RightModule(1, "rm-1"));
            add(new RightModule(2, "rm-2"));
            add(new RightModule(3, "rm-3"));
            add(new RightModule(4, "rm-4"));
            add(new RightModule(5, "rm-5"));
            add(new RightModule(6, "rm-6"));
            add(new RightModule(7, "rm-7"));
        }
    };

    private AtomicInteger count = new AtomicInteger(7);

    /**
     * 每3s更新一次rightModuleList的数据
     */
    @Scheduled(fixedRate = 3000)
    public void updateRightModuleList() {
        int i = (int) (Math.random() * rightModuleList.size());
        RightModule rm = rightModuleList.get(i);
        rm.setModuleName(rm.getModuleName().substring(0, 3) + count.incrementAndGet());
    }

    /**
     * 新增权限模块
     *
     * @param rightModule
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RequestResult addRightModule(@RequestBody RightModule rightModule) {
        RequestResult result = new RequestResult();

        rightModuleList.add(rightModule);
        result.setErrorNum("1000");
        result.setResult(rightModule);
        return result;
    }

    /**
     * 更新权限模块
     *
     * @param rightModule
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public RequestResult updRightModule(@RequestBody RightModule rightModule) {
        RequestResult result = new RequestResult();

        for (RightModule rm :
                rightModuleList) {
            if (rm.getRightModuleId() == rightModule.getRightModuleId()) {
                rm.setModuleName(rightModule.getModuleName());
            }
        }
        result.setErrorNum("1000");
        return result;
    }

    /**
     * 获取权限模块列表
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RequestResult getRightModuleList() {
        RequestResult result = new RequestResult();

        result.setErrorNum("1000");
        result.setResult(rightModuleList);
        return result;
    }

    /**
     * 长连接获取权限模块列表
     *
     * @return
     */
    @RequestMapping(value = "/longpolling/", method = RequestMethod.GET)
    public RequestResult getRightModuleListLongPolling() throws InterruptedException {
        RequestResult result = new RequestResult();
        Random rand = new Random();
        // 死循环 查询有无数据变化
        while (true) {
            Thread.sleep(300); // 休眠300毫秒，模拟处理业务等
            int i = rand.nextInt(100); // 产生一个0-100之间的随机数
            if (i > 20 && i < 56) { // 如果随机数在20-56之间就视为有效数据，模拟数据发生变化

                result.setErrorNum("1000");
                result.setResult(rightModuleList);
                return result;
            } else { // 模拟没有数据变化，将休眠 hold住连接
                Thread.sleep(1300);
            }
        }
    }

    @RequestMapping(value = "/push", produces = "text/event-stream;charset=UTF-8")
    public @ResponseBody
    String push() {
        Random r = new Random();
        //        try {
        //            Thread.sleep(5000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //SSE返回数据格式是固定的以data:开头，以\n\n结束
        return "data:Testing 1,2,3" + r.nextInt() + "\n\n";
    }
    //    @RequestMapping("/sseTest")
    //    public ResponseBodyEmitter handleRequest () {
    //
    //        final SseEmitter emitter = new SseEmitter();
    //        ExecutorService service = Executors.newSingleThreadExecutor();
    //        service.execute(() -> {
    //            for (int i = 0; i < 10; i++) {
    //                try {
    //                    emitter.send(LocalTime.now().toString() , MediaType.TEXT_PLAIN);
    //
    //                    Thread.sleep(200);
    //                } catch (Exception e) {
    //                    e.printStackTrace();
    //                    emitter.completeWithError(e);
    //                    return;
    //                }
    //            }
    //            emitter.complete();
    //        });
    //
    //        return emitter;
    //    }
}
