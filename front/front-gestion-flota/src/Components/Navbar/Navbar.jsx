import "./Navbar.css";
import { useLayout } from "../../context/LayoutContext";

function Navbar() {

    const { toggleSidebar } = useLayout();

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
                <span>Administrador</span>
            </div>

        </header>
    );
}

export default Navbar;