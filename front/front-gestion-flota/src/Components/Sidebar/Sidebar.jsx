import { Link } from "react-router-dom";
import { useLayout } from "../../context/LayoutContext";
import "./Sidebar.css";

function Sidebar() {

    const { sidebarOpen } = useLayout();

    const rol = localStorage.getItem("rol");

    return (

        <aside className={`sidebar ${sidebarOpen ? "open" : "collapsed"}`}>

            {/* Dashboard */}
            <Link to="/">
                <span className="icon">🏠</span>
                <span className="text">Dashboard</span>
            </Link>

            {/* Vehículos */}
            <Link to="/vehiculos">
                <span className="icon">🚛</span>
                <span className="text">Vehículos</span>
            </Link>

            {/* Conductores (Solo ADMIN) */}
            {
                rol === "ADMIN" && (
                    <Link to="/conductores">
                        <span className="icon">👨</span>
                        <span className="text">Conductores</span>
                    </Link>
                )}
            {/* Usuarios (Solo ADMIN) */}

            {rol === "ADMIN" && (
                    <Link to="/usuarios">
                        <span className="icon">👤</span>
                        <span className="text">Usuarios</span>

                    </Link>
                )
            }

            {/* Mantenciones */}

            <Link to="/mantenciones">

                <span className="icon">🔧</span>
                <span className="text">Mantenciones</span>

            </Link>

            {/* Documentación */}

            <Link to="/revisiones">

                <span className="icon">📋</span>
                <span className="text">Documentación</span>

            </Link>

            {/* Fallas */}

            <Link to="/fallas">

                <span className="icon">⚠️</span>
                <span className="text">Fallas</span>

            </Link>

        </aside>

    );

}

export default Sidebar;