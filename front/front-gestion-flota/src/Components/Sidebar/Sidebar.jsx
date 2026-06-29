import { Link } from "react-router-dom";
import { useLayout } from "../../context/LayoutContext";
import "./Sidebar.css";

function Sidebar() {

    const { sidebarOpen } = useLayout();

    return (
        <aside className={`sidebar ${sidebarOpen ? "open" : "collapsed"}`}>

            <Link to="/">

                <span className="icon">🏠</span>
                <span className="text">Dashboard</span>

            </Link>

            <Link to="/vehiculos">

                <span className="icon">🚛</span>
                <span className="text">Vehículos</span>

            </Link>

            <Link to="/conductores">

                <span className="icon">👨</span>
                <span className="text">Conductores</span>

            </Link>

            <Link to="/usuarios">

                <span className="icon">👤</span>
                <span className="text">Usuarios</span>

            </Link>

            <Link to="/mantenciones">

                <span className="icon">🔧</span>
                <span className="text">Mantenciones</span>
            </Link>

            <Link to="/revisiones">
                <span className="icon">📋</span>
                <span className="text">Revisiones</span>
            </Link>

            <Link to="/soap">
                <span className="icon">🛡️</span>
                <span className="text">SOAP</span>
            </Link>

            <Link to="/fallas">
                <span className="icon">⚠️</span>
                <span className="text">Fallas</span>
            </Link>

        </aside>
    );
}

export default Sidebar;