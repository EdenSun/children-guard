package eden.sun.childrenguard.server.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.service.IBaseService;

@Service
public class BaseServiceImpl implements IBaseService{
	protected Logger logger = Logger.getLogger(this.getClass());
}
