import logo from '../logopos.png';
import './App.css';
import AppContent from './AppContent';
import Header  from './Header';

function App() {
  return (
    <div>
      
      <Header pageTitle = "Medical Center" logoSrc={logo}/>
      <div className='container-fluid'>
          <AppContent/>
      </div>
    </div>
  );
}



export default App;

