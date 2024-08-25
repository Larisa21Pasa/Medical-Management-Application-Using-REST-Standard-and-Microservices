// // ErrorPopup.js
// import React, { useEffect } from 'react';
// import './ErrorPopup.css';

// const ErrorPopup = ({ message, onClose }) => {
//   useEffect(() => {
//     const timeoutId = setTimeout(() => {
//       onClose();
//     }, 5000);

//     return () => {
//       clearTimeout(timeoutId);
//     };
//   }, [onClose]);

//   return (
//     <div className="error-popup">
//       <h5>{message}</h5>
//     </div>
//   );
// };

// export default ErrorPopup;
