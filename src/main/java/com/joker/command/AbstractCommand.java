package com.joker.command;


import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

import com.joker.command.rule.CommandRule;
import com.joker.entity.CommandKey;
import com.joker.rx.observer.Observable;
import com.joker.rx.observer.Subscriber;
import com.joker.threadpool.JokerThreadPool;
import com.joker.threadpool.JokerThreadPool.Factory;
import com.joker.threadpool.ThreadPoolFactory;
import com.joker.util.Action;
import com.joker.util.Func;
import com.joker.util.JokerScheduler;
import com.joker.util.Obserbable;


public abstract class AbstractCommand<R> {
	protected AtomicReference<CommandState> commandState = new AtomicReference<CommandState>(CommandState.NOT_STARTED);
	protected CommandRule commandRule;
	protected JokerThreadPool threadPool;
	protected enum CommandState {
        NOT_STARTED, OBSERVABLE_CHAIN_CREATED, USER_CODE_EXECUTED, UNSUBSCRIBED, TERMINAL
    }
	
	protected enum ThreadState {
		VALUABLE,IS_DOWN,IS_RUN,TERMINAL
    }
    
	protected AbstractCommand(String key,Class classes) {
		CommandKey commandKey = new CommandKey();
		commandKey.setKey(key);
		commandKey.setRule_class(classes);
//		threadPool = ThreadPoolFactory.getPoolInstance(commandKey);
		threadPool = Factory.getInstance(commandKey);
		this.commandState.set(CommandState.NOT_STARTED);
		try {
			this.commandRule = CommandRuleFactory.createCommandRule(commandKey);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
//	//通知
//	@SuppressWarnings("static-access")
//	public Obserbable<R> toObservable() {
//		Obserbable<R> obserbable = new Obserbable<R>();
//		obserbable.setFunc(new Func<R>() {
//			@SuppressWarnings("unchecked")
//			public R call() {
//				commandState.set(CommandState.USER_CODE_EXECUTED);
//				Runnable runnable = new Runnable() {
//					public void run() {
//						doRun();
//					}
//				};
//				return (R) threadPool.submit(runnable);
//			}
//		});
//		
//		
//		Action terminateAction = new Action() {
//			public void call() {
////				 if (_cmd.commandState.compareAndSet(CommandState.OBSERVABLE_CHAIN_CREATED, CommandState.TERMINAL)) {
////	                    handleCommandEnd(false); //user code never ran
////	                } else if (_cmd.commandState.compareAndSet(CommandState.USER_CODE_EXECUTED, CommandState.TERMINAL)) {
////	                    handleCommandEnd(true); //user code did run
////	                }
//			}
//		};
//		Action unsubscribeAction = new Action() {
//			public void call() {
//			}
//		};
//		Action completedAction = new Action() {
//			public void call() {
//				
//			}
//		};
//		//设置obserbable的处理方式
//		obserbable.doOnTerminate(terminateAction);
//		obserbable.doOnUnsubscribe(unsubscribeAction);
//		obserbable.doOnCompleted(completedAction);
//		this.commandState.set(CommandState.OBSERVABLE_CHAIN_CREATED);
//		return obserbable;
//	}
	
	//将command组装到Observable中
	@SuppressWarnings("unchecked")
	public Observable<R> toObservable() {
		commandState.set(CommandState.OBSERVABLE_CHAIN_CREATED);
		return (Observable<R>) Observable.create(new Observable.OnSubscribe() {
			public void call(Subscriber subscriber) {
				subscriber.onNext(doRun());
				subscriber.onCompleted();
			}
		}).subscribeOn(new JokerScheduler((ThreadPoolExecutor) threadPool.geExecutorService()));
	}
	
	
	
	protected abstract R doRun();
	
	
}
