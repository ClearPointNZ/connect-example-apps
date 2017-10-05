import * as React from 'react';
import * as ReactDOM from 'react-dom';
import './css/index.css';

import { SlackMessage } from './components/SlackMessage';

ReactDOM.render(
	<SlackMessage/>,
  
	document.getElementById('app')
);