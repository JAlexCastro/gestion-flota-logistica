import "./Navbar.css";
import { useLayout } from "../../context/LayoutContext";
import { useNavigate } from "react-router-dom";

function Navbar() {

    const { toggleSidebar } = useLayout();

    const navigate = useNavigate();

    const usuario = localStorage.getItem("usuario");

    const cerrarSesion = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("usuario");

        navigate("/login");

    };

    return (

        <header className="navbar">

            <div className="left">

                <button
                    className="menu-btn"
                    onClick={toggleSidebar}
                >
                    ☰
                </button>

                <h2>Gestión de Flota</h2>

            </div>

            <div className="right">

                <span className="usuario">

                    👤 {usuario}

                </span>

                <button
                    className="logout-btn"
                    onClick={cerrarSesion}
                >

                    Cerrar sesión

                </button>

            </div>

        </header>

    );

}

export default Navbar;